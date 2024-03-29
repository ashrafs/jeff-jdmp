/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.node;

import org.jdmp.core.script.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class AAndLevel7 extends PLevel7
{
    private PLevel7 _left_;
    private TAnd _and_;
    private PLevel6 _right_;

    public AAndLevel7()
    {
        // Constructor
    }

    public AAndLevel7(
        @SuppressWarnings("hiding") PLevel7 _left_,
        @SuppressWarnings("hiding") TAnd _and_,
        @SuppressWarnings("hiding") PLevel6 _right_)
    {
        // Constructor
        setLeft(_left_);

        setAnd(_and_);

        setRight(_right_);

    }

    @Override
    public Object clone()
    {
        return new AAndLevel7(
            cloneNode(this._left_),
            cloneNode(this._and_),
            cloneNode(this._right_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAndLevel7(this);
    }

    public PLevel7 getLeft()
    {
        return this._left_;
    }

    public void setLeft(PLevel7 node)
    {
        if(this._left_ != null)
        {
            this._left_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._left_ = node;
    }

    public TAnd getAnd()
    {
        return this._and_;
    }

    public void setAnd(TAnd node)
    {
        if(this._and_ != null)
        {
            this._and_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._and_ = node;
    }

    public PLevel6 getRight()
    {
        return this._right_;
    }

    public void setRight(PLevel6 node)
    {
        if(this._right_ != null)
        {
            this._right_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._right_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._left_)
            + toString(this._and_)
            + toString(this._right_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._left_ == child)
        {
            this._left_ = null;
            return;
        }

        if(this._and_ == child)
        {
            this._and_ = null;
            return;
        }

        if(this._right_ == child)
        {
            this._right_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._left_ == oldChild)
        {
            setLeft((PLevel7) newChild);
            return;
        }

        if(this._and_ == oldChild)
        {
            setAnd((TAnd) newChild);
            return;
        }

        if(this._right_ == oldChild)
        {
            setRight((PLevel6) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
